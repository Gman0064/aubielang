## The AUBIElang Programming Language
The AUBIElang Programming Language is a strongly typed, intepreted programming language that runs through the JVM via a Java interpreter. It is designed to be a verbose language based around built-in keywords, while also ignoring whitespace as a formatting/parsing technique. Scripts written in this language are saved with the ***.aub extension.***

The language and interpreter were written as an entry to the 2020 AUHackathon in Auburn, AL, with development lasting over February 8th-9th. It has support for 8 built-in math functions, console output, if-statement branching, for loops, user-defined variables, and user-defined functions. AUBIElang supports only 2 types: num (a double), and string.

### Authors:  
Josh Coward  
Garrett Dickinson  
Saksham Geole  
Matt Wurstner  

## Standard Functions:
print a (Print to console)  
sum a b c... (Addition)  
sub a b c... (Subtraction)  
div a b c... (Division)  
mult a b c... (Multiplication)  
log a b (Logarithmic)  
pow a b (Exponents)  
mod a b (Modulus)  
fact a (Factorials)  

## Keywords and Comparators:
def a (type num:string) (value)  
  
if (comparison)  
  elif ...  
    ...  
  else  
    ...  
done  
  
loop a  
...  
next  

func (name) (parameter type, name)  
...  
end  

equals (equal to)  
nequals (not equal to)  
gequals (greater than or equal to)  
lequals (less than or equal to)  
lthan (less than)  
gthan (greater than)  
