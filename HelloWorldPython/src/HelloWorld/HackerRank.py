'''
Created on May 4, 2015

@author: Tim
'''

if __name__ == '__main__':

    
    dummy = input()
    inA = input()
    dummy2 = input()
    inB = input()
    
    listA = inA.split()
    listB = inB.split()
    
    setA = set(map(int, listA))
    setB = set(map(int, listB))
    
    setDiffA = setA.difference(setB)
    setDiffB = setB.difference(setA)
    
    setFinal = setDiffA.union(setDiffB)
    listFinal = sorted(setFinal)
    for i in listFinal: print(i)
    
    f = input()
    l = input()
    print("Hello ", f, " ", l, "! You just delved into python.", sep="")
    
    a = float(input(''))
    b = float(input(''))
    print ("%.2f" % round(a+b,2))
    print ("%.2f" % round(a-b,2))
    print ("%.2f" % round(a*b,2))
    print ("%.2f" % round(a/b,2))
    print ("%.2f" % round(a//b,2))
    
    