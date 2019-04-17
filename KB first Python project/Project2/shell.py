import argparse
import sys
import re
import os
import tempfile
from kb import KB

# Python version 2.7.X must be used
assert (sys.version_info[0] == 3 and sys.version_info[1] >= 5),"Please use python version >= 3.5"

'''
This class handles the input and output into and out of a knowledge base object. It also
handles importing data to and saving data from the knowledge base.

DON'T CHANGE THIS FILE!
'''
class Shell:
    
    '''
    This function initliazes a Shell object with a stopping condition regex and a newly created
    knolwedge base.

    input: kb => knowledge base that is used by the shell
    '''
    def __init__(self,kb):
        self.stop_cond = re.compile('^[Qq]([uU][iI][tT])?$')
        self.kb = kb

    '''
    This function starts up the shell and loops over input data. If there is no input file, the
    shell will read input typed into the terminal manually.

    input: input_file => the address for a data file for input facts and queries to be fed to 
                         the knowledge base. In the case an input file address is provided and
                         is valid, there is no manual entering of data. Default value is ''.
           prior_kb => the address of a previously saved knowledge base that will be loaded into
                       current knowledge base, if the address is valid. Default value is ''.
           save_kb => the address of the file to save the facts in a database to at the end of the
                      run.
           output_file => the address of a file in which to save all the input and output from
                          the system.
           print_bool => if True, prints out input/output to terminal, else ignores printing 
                         input/output to terminal
           temp_output => a boolean flag indicating that a temporary file is to be used for
                          the output file. This is used for testing purposes.
    '''
    def start(self,input_file,prior_kb,save_kb,output_file=None,print_bool=True,temp_output=False):
        save_kb_file = None
        tmp_bool = False
        if save_kb:
            x = ''
            save_kb_bool = True
            while (save_kb_bool and os.path.isfile(save_kb)):
                x = input('File ' + save_kb + ' already exists!\nAre you SURE you want to OVERWRITE this file? (yes or no): ').strip()
                while (x != 'yes' and x != 'no'):
                    x = input('Please type \'yes\' or \'no\': ').strip()
                if x == 'no':
                    x = input('Would you like to name a different file? (yes or no): ').strip()
                    while (x != 'yes' and x != 'no'):
                        x = input('Please type \'yes\' or \'no\': ').strip()
                    if x == 'no':
                        print('OK, the kb will NOT be saved.')
                        save_kb_bool = False
                        save_kb = None
                    else:
                        save_kb = input('Please enter new file name: ').strip()
                else:
                    save_kb_bool = False
            if save_kb:
                if prior_kb == save_kb:
                    tmp_bool = True
                    save_kb_file = tempfile.mkstemp()#os.tmpfile()
                    save_kb_file = open(save_kb_file[0],'r+')
                else:
                    save_kb_file = open(save_kb,'r+')

        if prior_kb:
            if os.path.isfile(prior_kb):
                with open(prior_kb,'r') as f:
                    for line in f.readlines():
                        response = self.kb.process(line.lower())
                        if save_kb_file and response == self.kb.responses['OK']:
                            save_kb_file.write(line.lower().strip() + '\n')
            else:
                print('Please provide a valid prior_kb file!\nExiting...')
                exit(-1)
        if tmp_bool:
            tmp_file = save_kb_file
            save_kb_file = open(save_kb,'r+')
            tmp_file.seek(0)
            for line in tmp_file.readlines():
                save_kb_file.write(line)
            tmp_file.close()

            
        if output_file and (not input_file or os.path.isfile(input_file)) and not temp_output:
            x = ''
            output_bool = True
            while (output_bool and os.path.isfile(output_file)):
                x = input('File ' + output_file + ' already exists!\nAre you SURE you want to OVERWRITE this file? (yes or no): ').strip()
                while (x != 'yes' and x != 'no'):
                    x = input('Please type \'yes\' or \'no\': ').strip()
                if x == 'no':
                    x = input('Would you like to name a different file? (yes or no): ').strip()
                    while (x != 'yes' and x != 'no'):
                        x = input('Please type \'yes\' or \'no\': ').strip()
                    if x == 'no':
                        print('OK, the output will NOT be saved.')
                        output_bool = False
                        output_file = None
                    else:
                        output_file = input('Please enter new file name: ').strip()
                else:
                    output_bool = False
            if output_file:
                output_file = open(output_file,'r+')
        if temp_output:
            output_file = tempfile.mkstemp() #os.tmpfile()
            output_file = open(output_file[0],'r+')
                                   
        if input_file:
            output = ''
            if os.path.isfile(input_file):
                with open(input_file,'r') as f:
                    for line in f.readlines():
                        output = output + 'Enter knowledge or query: ' + line.strip() + '\n'
                        response = self.kb.process(line.lower())
                        if save_kb_file and response == self.kb.responses['OK']:
                            save_kb_file.write(line.lower().strip() + '\n')
                        output = output + response + '\n'
                    if print_bool:
                        print(output[:-1])
            else:
                print('Please provide a valid input file!\nExiting...')
                exit(-1)
        else:
            output = ''
            x = input('Enter knowledge or query: ')
            while not self.stop_cond.findall(x):
                output = output + 'Enter knowledge or query: ' + x.strip() + '\n'
                response = self.kb.process(x.lower())
                if save_kb_file and response == self.kb.responses['OK']:
                    save_kb_file.write(x.lower().strip() + '\n')
                print(response)
                output = output + response + '\n'
                x = input('Enter knowledge or query: ')
        if save_kb_file:
            save_kb_file.close()
        if output_file:
            output_file.write(output[:-1])
        if temp_output:
            return output_file
        elif output_file:
            output_file.close()
            return None

if __name__ == '__main__':
    parser = argparse.ArgumentParser('Parser for 421 Project 3')
    parser.add_argument('--input', '-i',type=str,default='',help='file name for providing input instead of manually entering input')
    parser.add_argument('--prior_kb','-p',type=str,default='',help='file name for loading a saved knowledge base')
    parser.add_argument('--save_kb','-s',type=str,default='',help='file name to save the knowledge base to')
    parser.add_argument('--responses','-r',type=str,default='',help='file name for stored agent responses')
    parser.add_argument('--output','-o',type=str,default='',help='file name for output')
    args = parser.parse_args()
    kb = KB(args.responses)
    print('Input looks like:\n\tA(n) <word1> is a(n) <word2>.\n\tA(n) <word1> is not a(n) <word2>.\n' +
          '\t<proper noun> is a <word1>.\n\t<proper noun> is not a <word1>.\n\t' +
          'Is a(n) <word1> a(n) <word2>?\n\tIs <proper noun> a <word1>?\n\tIs a <word1> <proper noun>?')
    print('Type \'quit\' or \'q\' to exit.\n')
    Shell(kb).start(args.input,args.prior_kb,args.save_kb,args.output)
