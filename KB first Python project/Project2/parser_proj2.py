import re

'''
A class that is set up to parse the input to a knowledge base into a fact or 
query, such that elements of the fact/query are separated as necessary.

DON'T CHANGE THIS FILE!
'''
class Parser:

    def __init__(self):
        self.regex_input = re.compile('^\s*([Aa]n? +)?([\w]+) +(is) +(not )?(an? +)([\w]+)[.]?\s*$')
        self.regex_query = re.compile('^\s*([Ii]s) +(an? +)?([\w]+) +(an? +)?([\w]+)\??\s*$')

    def parse_fact(self,input):
        matches = self.regex_input.findall(input)
        return matches

    def parse_query(self,query):
        matches = self.regex_query.findall(query)
        return matches

        

