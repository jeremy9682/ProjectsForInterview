import os
import sys
import unittest
sys.path.append(os.path.abspath(os.pardir))
from shell import Shell
from kb import KB

class Proj2Test(unittest.TestCase):
    def generic_test(self,test_num):
        base_addr = os.getcwd()
        input_addr = base_addr + '/test' + str(test_num) + '.input.txt'
        test_addr = base_addr + '/test' + str(test_num) + '.output.txt'
        resp_addr = base_addr + '/test' + str(test_num) + '.responses.txt'
        kb = KB(resp_addr)
       
        output_file = Shell(kb).start(input_addr,None,None,None,False,True)
        output_file.seek(0)
        with open(test_addr,'r') as t:
            student_output = output_file.readlines()
            test_output = t.readlines()
            print("testing")
            print(test_num)
            for (line1,line2) in zip(student_output,test_output):
                self.assertEqual(line1,line2)
        
        output_file.close() 

    
    def test1(self):
        for x in range(37,41):
            print('test')
            print(x)
            self.generic_test(x)
            print(" ")
            """
    def test2(self):
        print('test37')
        self.generic_test(37)
        print(" ")

        """


"""
    def test2(self):
        self.generic_test(2)

    def test3(self):
        self.generic_test(3)

    def test4(self):
        self.generic_test(4)

    def test5(self):
        self.generic_test(5)

    def test6(self):
        self.generic_test(6)

    def test7(self):
        self.generic_test(7)

    def test8(self):
        self.generic_test(8)

    def test9(self):
        self.generic_test(9)

    def test10(self):
        self.generic_test(10)

    def test11(self):
        self.generic_test(11)

    def test12(self):
        self.generic_test(12)

    def test13(self):
        self.generic_test(13)

    def test14(self):
        self.generic_test(14)

    def test15(self):
        self.generic_test(15)

    def test16(self):
        self.generic_test(16)

    def test17(self):
        self.generic_test(17)

    def test18(self):
        self.generic_test(18)

    def test19(self):
        self.generic_test(19)

    def test20(self):
        self.generic_test(20)

    def test21(self):
        self.generic_test(21)

    def test22(self):
        self.generic_test(22)

    def test23(self):
        self.generic_test(23)

    def test24(self):
        self.generic_test(24)

    def test25(self):
        self.generic_test(25)

    def test26(self):
        self.generic_test(26)

    def test27(self):
        self.generic_test(27)

    def test28(self):
        self.generic_test(28)

    def test29(self):
        self.generic_test(29)

    def test30(self):
        self.generic_test(30)

    def test31(self):
        self.generic_test(31)

    def test32(self):
        self.generic_test(32)

    def test33(self):
        self.generic_test(33)

    def test34(self):
        self.generic_test(34)

    def test35(self):
        self.generic_test(35)

    def test36(self):
        self.generic_test(36)

    def test37(self):
        self.generic_test(37)

    def test38(self):
        self.generic_test(38)

    def test39(self):
        self.generic_test(39)

    def test40(self):
        self.generic_test(40)
        """

if __name__ == '__main__':
    unittest.main()
                

