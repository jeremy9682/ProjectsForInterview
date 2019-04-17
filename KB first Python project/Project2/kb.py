
import os
import re
from parser_proj2 import Parser

class Node:

    value = None
    next = None
    def __init__(self, newValue,newNext):
        self.value = newValue
        self.next = newNext
'''
A knowledge base (KB) class that will accept facts, either positive or negative,
and answer queries on the facts that it knows. It will also detect contradictions
in new facts when compared to knowledge it already knows.
'''
class KB:
    
    '''
    This function initializes the datastructures for the KB object such that it can
    store information that it learns. You may want to consider a separate data
    structure for negative facts (e.g. A dog is not a cat.)

    input: respones => address for a file containing alternative responses from the
                       default options
    '''
    
    
    def __init__(self, responses=None):
        self.parser = Parser() # don't change this line
        self.responses = self.get_responses(responses) # don't change this line
        # INITIALIZE KNOWLEDGE BASE PROPERLY BELOW

        # A list contains two lists, one for is relationship nodes, another for is not relationship nodes
        self.kb = [[],[]]

    '''
    This function will retrieve alternative responses, if any are available. If there
    are no alternative respones, the default responses in their entirety will be
    returned. DO NOT CHANGE THIS FUNCTION.

    input: input => address for a file containing alternative responses from the
                       default options. If the file does not exist, the default
                       responses are returned in their entirety.
    '''
    def get_responses(self,input):
        responses = {'Y':'yes','N':'no','IDK':'I don\'t know','IK': 'I know', 
                     'CONTR':'contradiction', 'OK':'ok', 'INV': 'invalid', 
                     'SMT': 'sometimes'}
        if input and os.path.isfile(input):
            resp_regex = re.compile('^\s*(Y|N|IDK|IK|CONTR|OK|INV|SMT)\s*=>\s*([\w\"\'<>,.!:;-]+(\s+[\w\"\'<>,.!:;-]+)*)\s*$')
            with open(input) as f:
                for line in f.readlines():
                    new_resp = resp_regex.findall(line)
                    if new_resp:
                        new_resp = new_resp[0]
                        responses[new_resp[0]] = new_resp[1]
        return responses
        
    '''
    This function will accept an input tuple (either a positive or negative fact) and
    add said fact to the object's knowledge base is appropriate. The new fact could contradict
    an old fact, in which case the new fact should NOT be added to the knowledge base.
    Alternatively, if the fact already exits, nothing should be done and the 'IK' response
    should be returned.

    input: input => a fact tuple containing the matched items from the parser regular expression.
                    e.g. ('a','dog','is','','an','animal') or ('a','dog','is','not','a','cat')

    output: the 'OK' response if a fact is added to the knowledge base
            the 'IK' response if a fact is already known
            the 'CONTR' response if a new fact results in a contradiction with the current
                knowledge base
    '''
    def add_to_kb(self,input):
        print("in add to kb function now")
        input_tuple = input[0] # e.g. ('a','dog','is','','an','animal')
        subject = None
        predicate = None
        
        #deep copy the knowladge base
        currKB = list(self.kb)

        print(input_tuple)
        # when positive
        if 'not ' not in input_tuple: 
            # check subject     
            
            if input_tuple[0] == 'a ' or input_tuple[0] == 'an ' : 
                subject = input_tuple[0] + input_tuple[1]
               
                #get predicate
                predicate = input_tuple[4] + input_tuple[5]   
                           
            else:
                #proper names without a or an
                subject = input_tuple[1]
                predicate = input_tuple[4] + input_tuple[5]

"""
            print("in fact sub and pred")
            print(subject)
            print(predicate)    
"""
            # when list all empty
            if self.kb[0] == None and self.kb[1] == None: 
                newNode = Node(subject,None)
                newNode.next = Node(predicate,None)
                self.kb[0].append(newNode)
            
            #corner case  x->x
            if subject == predicate:  
                return self.responses['IK']
            
            #Check contradiction 
            if self.detect_contradiction(subject,predicate,0) == True : 
                return self.responses['CONTR'] 

            # check if x->y already exists
            if self.checkChildren(subject,predicate,0) == True :
                return self.responses['IK']
        
            # Then try to add into copied kb
            
            subNode = Node(subject,None)
            preNode = Node(predicate,None)     
            subNode.next = preNode

            #go through other nodes that already exist in the list and check if they contain subject Node
            for curr in currKB[0]:
                x = curr
                while x != None:
                    if x.value == subject and x.next == None:
                        #assign node after subject node for check relationship in the futurer
                        x.next = preNode
                        break
                    x = x.next
            
             # check cycle after adding nodes
            if self.checkCycle(0,currKB) == True: 
                return self.responses['CONTR'] 
           
            # add sub->pred into copied kb   
            currKB[0].append(subNode)
            
            #corner case  consider the transitive
            #when q !-> v but add u -> v. Lead to add parent nodes to is not fact to u like  q !-> u   
            #find predicate's children
            parentList = self.findParentNodes(predicate,1)
            
            
            #then add subject !-> children into isnot kb
            if len(parentList) != 0:
                for parent in parentList:
                    y = Node(subject,None)
                    x = Node(parent.value,y)
                    currKB[1].append(x) 

            # when add z->x. go to is not kb to 
            # check predicate's children's node and lead to subject !-> children nodes  
            childList = self.findChildNodes(predicate,1)
            if len(childList) != 0:
                for child in childList:
                    y = Node(child.value,None)
                    x = Node(subject,y)
                    currKB[1].append(x)






             # check cycle after adding nodes
            if self.checkCycle(0,currKB) == True: 
                return self.responses['CONTR'] 

            self.printKB()   
                
            #Assign the current kb to self.kb
            self.kb = currKB
            return  self.responses['OK'] 
            
        # negative 
        else:  
              # get subject     
              #when subject starts with 'a' or 'an'
            if input_tuple[0] == 'a ' or input_tuple[0] == 'an ': 
                subject = input_tuple[0] + input_tuple[1]
                
               #get predicate
                predicate = input_tuple[4] + input_tuple[5]   
            else:
                #proper names without a or an
                subject = input_tuple[1]
              #  print(subject)
                predicate = input_tuple[4] + input_tuple[5]
            #    print(predicate)
            """
            print("in fact sub and pred")
            print(subject)
            print(predicate)     
            """
            # when list all empty
            if self.kb[0] == None and self.kb[1] == None: 
                newNode = Node(subject,None)
                newNode.next = Node(predicate,None)
                self.kb[1].append(newNode)

            #corner case  x !-> x
            if subject == predicate:  
                return self.responses['CONTR']

            #Check contradiction 
            if self.detect_contradiction(subject,predicate,1) == True: 
                return self.responses['CONTR'] 
            # check if x->y already exists
            if self.checkChildren(subject,predicate,1) == True :
                return self.responses['IK']

            # Then try to add into copied kb
            preNode = Node(predicate,None)
            subNode = Node(subject,None)
            # add sub->pred into copied kb        
            subNode.next = preNode
            currKB[1].append(subNode)
            # when sub->pred added, automatically add pre -> sub into is not kb
            newPred = Node(predicate,None)
            newSub = Node(subject, None)
            newPred.next = newSub
            currKB[1].append(newPred)


            # when add a not fact x !-> y inside. Any node(a) that has x as a children node will lead to a !-> y
            # like x -> y -> z -> w !-> v then x,y,z all !-> v

            #find the parent nodes of subject
            parentList = self.findParentNodes(subject,0)
           

            if len(parentList) != 0:
                for parent in parentList:
                    x = Node(predicate,None)
                    y = Node(parent.value,x)
                    currKB[1].append(y)

            #Assign the current kb to self.kb
            self.kb = currKB

            self.printKB() 

            return  self.responses['OK'] 


        return self.responses['INV']

    def findChildNodes(self, subject,listNum):
        returnList = []
        for currNode in self.kb[listNum]:
            x = currNode
            while x != None:
                #find parent node first
                if x.value == subject:
                    y = x.next 
                    while y != None:
                        newNode = Node(y.value,None)
                        returnList.append(newNode)
                        y = y.next
                x = x.next
        #remove duplicate elements
        final_list = [] 
        valSet = set()
        for num in returnList: 
            if num.value not in valSet: 
                final_list.append(num) 
                valSet.add(num.value)

        return final_list


    # helper function to check predicate's parent nodes in list[listNum]
    def findParentNodes(self, predicate,listNum):
        returnList = []
        for currNode in self.kb[listNum]:
            x = currNode
            tempList = []
            while x != None:
                if x.value != predicate:
                    y = Node(x.value, None)
                    tempList.append(y)
                else:# when x value equals to predicate, the previous nodes are all parent nodes
                    returnList = tempList + returnList
                x = x.next
        # get a list with elements's value
        valList = []
        for x in returnList:
            valList.append(x.value)
        #remove duplicate elements
        final_list = [] 
        valSet = set()
        for num in returnList: 
            if num.value not in valSet: 
                final_list.append(num) 
                valSet.add(num.value)

        return final_list




    #helper function to check if cycle exists
    def checkCycle(self, listNum, copiedKB):
        currKB = copiedKB[listNum]
       
        #print("in checkCycle function")
        for currNode in currKB:
            storeList = []
            x = currNode
            while x != None:
                if x.value not in storeList:
                    storeList.append(x.value)
                    x = x.next
                else:
                    return True    

        return False        
    #helper funcation to print out kb to debug
    def printKB(self):
        
        print("is relationship kb")
        for x in self.kb[0]:
          #  print("go to next element")
       #     print(x.value)
            curr = x
            #print("child Nodes")
            while curr != None:
                print(curr.value)
                curr = curr.next
        

        print("is NOT relationship kb")
        for x in self.kb[1]:
            print("go to next element")
         #   print(x.value)
            curr = x
            while curr != None:
                print(curr.value)
                curr = curr.next    
        print()

    
    #helper function to check if predicate is subject's children node
    def checkChildren(self, subject, predicate, listNum):
        #print("in check children function")
       # print("check kb now for")
       # print(subject)
        #print(predicate)
 #       self.printKB()


        for curr in self.kb[listNum]:
            
            if curr.value == subject:
                x = curr
                while x!= None:
                    if x.value == predicate:
                        return True
                    #print(x.value)
                    x = x.next
                   # print("x = x.next now")
                   
        
        return False





    '''
    This function detects a contradiction in the current knowledge base given a new fact
    that is attempted to be added to the knowledge base. A cycle counts as a contradiction.
    
    input: first_item => the first item (e.g. dog in 'a dog is an animal') in an input fact
           second_item => the second item (e.g. animal in 'a dog is an animal') in an input fact
           not_indicator => indicates whether the input fact was a positive or negative fact
                            (i.e. whether it includes 'not' in the fact)

    output: True if a contradiction is found, False otherwise
    '''
    def detect_contradiction(self,first_item,second_item,not_indicator):
        print("in detect ccontradiction function now")
        #is fact
        if not_indicator == 0: 
            # when x !-> y exist, x -> y and y->x both contradiction
            #check isnot kb
            for currNode in self.kb[1]:
                if currNode.value == first_item:
                    if currNode.next.value == second_item: 
                        return True
                if  currNode.value == second_item:
                    if currNode.next.value == first_item:
                        return True
        
        else:# is not fact 
            #when x !-> x 
            if first_item == second_item: return True
            # when x->y,  x !-> y and y !-> x both contradiction
            """
            print("check contradiction for")
            print(first_item)
            print(second_item)

            print("checkChildren now")
            """
            if self.checkChildren(first_item,second_item,0) or self.checkChildren(second_item,first_item,0):
                return True
           
            for currNode in self.kb[0]:
                if currNode.value == first_item:
                    if currNode.next.value == second_item: 
                        return True
                if  currNode.value == second_item:
                    if currNode.next.value == first_item:
                        return True
                
       # print(" in detect contradiction function to detect cycle now")
        #detect cycle
        if self.checkCycle(0,self.kb) == True: return True

        return False

    '''
    This function accepts a query tuple and answers said query based on the current information
    in the knowledge base.

    input: query => a query tuple containing the matched items from the parser regular expression.
                    e.g. ('Is', 'a', 'dog','an','animal') or ('Is','','Fido','a','dog')

    output: the 'Y' response if the answer to the query is yes
            the 'N' response if the answer to the query is no
            the 'IDK' response if the query can't be answered based on the current state of the
                knowledge base
            the 'SMT' response if the query may be true on occasion
    '''
    def answer_query(self,query):
    
        query_tuple = query[0] # e.g. ('is','a','dog','an','animal')

        subject = None
        predicate = None
        #deep copy the knowladge base
        
        print(query_tuple)
        #get subject 
        if query_tuple[1] == 'a ' or query_tuple[1] == 'an ':
            subject = query_tuple[1] + query_tuple[2]
            # get predicate
            if query_tuple[3] == 'a ' or query_tuple[3] == 'an ':
                predicate = query_tuple[3] + query_tuple[4]
            else:
                predicate = query_tuple[3]
        else:# proper names withuot a or an
            subject = query_tuple[2]
             # get predicate
            if query_tuple[3] == 'a ' or query_tuple[3] == 'an ':
                predicate = query_tuple[3] + query_tuple[4]
            else:
                predicate = query_tuple[4]
                """
        print("query sub and pred")
        print(subject)
        print(predicate)
        """

        #check yes situations
        #try to add into temp kb. if it return 'I know',then it is yes situation for query
         
        #corner case  x->x?
        if subject == predicate:  
            return self.responses['Y']
        # check if x->y already exists

      #  print(self.kb[0][0].next.value)
        if self.checkChildren(subject,predicate,0) == True :
            return self.responses['Y']

        #check no situations
        #When it meets contradiction then return 'no'
        if self.detect_contradiction(subject,predicate,0) == True:
            return self.responses['N']
        
        #check sometime situations
        # first case: when x->y exist, reverse y -> x?
        if self.checkChildren(predicate,subject,0) == True:
            return self.responses['SMT']
        
        # Second case: x->y and x->z exist , then z->y? z,y share same parent node
        subList = self.findParentNodes(subject,0)
        preList = self.findParentNodes(predicate,0)
        
       # print("sublist")
       # print(subList[0].value)
       # print("preList")
        # print(preList[0].value)

        #check if two lists have elements in common
        for x in subList:
            for y in preList:
                 if x.value == y.value:
                     return self.responses['SMT']
            
            
          



        return self.responses['IDK']

       # return self.responses['INV']

    '''
    This function accepts the raw input and parses said input. If the input is a query, then
    'answer_query' is called with the parsed input. If the input is a fact, then 'add_to_kb'
    is called with the parsed input. If the input matches neither a query or a fact, then
    the 'INV' response is returned. DO NOT CHANGE THIS FUNCTION

    input: input => input into the knowledge base

    output: the response of the query or addition to the knowledge base
            the 'INV' response if the input isn't a valid match for a query or a fact
    '''
    def process(self,input):
        parsed_input = self.parser.parse_fact(input)
        if not parsed_input:
            parsed_input = self.parser.parse_query(input)
            if not parsed_input:
                return self.responses['INV']
            else:
                return self.answer_query(parsed_input)
        else:
            return self.add_to_kb(parsed_input)
