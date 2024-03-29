Simple Database
==================
You task is create a very simple database, which has a very limited command set. We aren't going to be storing that much data, so you don't have to worry about storing anything on disk; in-memory is fine. All of the commands going to be fed to you one line at a time via stdin, and your job is the process the commands and perform whatever operation the command dictates. Here are the basic commands you need to handle:

* `SET [name] [value]`: Set a variable [name] to the value [value]. Neither variable names or values will ever contain spaces.
* `GET [name]`: Print out the value stored under the variable [name]. Print NULL if that variable name hasn't been set.
* `UNSET [name]`: Unset the variable [name]
* `END`: Exit the program

So here is a sample input:

`SET a 10`   
`GET a`   
`UNSET a`   
`GET a`   
`END`   

And its corresponding output:

`10`   
`NULL`   

Now, as I said this was a database, and because of that we want to add in a few transactional features to help us maintain data integrity. So there are 3 additional commands you will need to support:

* `BEGIN`: Open a transactional block
* `ROLLBACK`: Rollback all of the commands from the most recent transaction block. If no transactional block is open, print out INVALID ROLLBACK
* `COMMIT`: Permanently store all of the operations from any presently open transactional blocks   

Our database supports nested transactional blocks as you can tell by the above commands. Remember, ROLLBACK only rolls back the most recent transaction block, while COMMIT closes all open transactional blocks. Any command issued outside of a transactional block commits automatically.

Here are some sample inputs and expected outputs using these commands:

Input:
========
`BEGIN`  
`SET a 10`  
`GET a`  
`BEGIN`  
`SET a 20`  
`GET a`  
`ROLLBACK`  
`GET a`  
`ROLLBACK`  
`GET a`  
`END`  

Output:
========
`10`  
`20`  
`10`  
`NULL`  

Input:
========
`BEGIN`  
`SET a 30`  
`BEGIN`  
`SET a 40`  
`COMMIT`  
`GET a`  
`ROLLBACK`  
`END`  

Output:
=======
`40`  
`INVALID ROLLBACK`  

--
Problem created by [Thumbtack](http://www.thumbtack.com/challenges)
