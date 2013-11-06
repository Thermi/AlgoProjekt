Hashtable
=========
This program implements a small program to create a hashtable with an arbitrary
number of entries, consisting of toupels of a 32 bit SHA-1 digest and a plain
text password. After creation of the hashtable, it requires a user input of a
password, which is then looked up in the hashtable and a corresponding message
gets printed to stdout, confirming or denying the existence of the password/collision
in the hashtable.