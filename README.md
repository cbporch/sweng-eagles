# sweng-eagles

All dependencies should be pulled by Maven when you first run anything.

*Code requires Java 1.8*

If you package the project using Maven like so:
`mvn package`
it should generate a .jar file in the target directory that should run a Driver
class that opens the two GUIs created so far. They may generate with one window on top of the other.


To enter confidential words into the test database, run **DatabaseInput.java**, in scanner.dbEntry. Currently,
as of 10.27.16, you can enter one word and one phrase at a time, there is a radio button to mark whether or not you want
synonyms added, a radio button to mark whether the existence of a number adjacent to that word makes it
more confidential (# Dependent), and the probability that a word is confidential. There is also a button to select
a CSV file to upload, but that currently is non-functional. The probability field defaults to 1.0.

(The number dependent radio button stores a value in the database, but is currently not used in score processing.)
(Code has been written to upload .csv files, but file selection has not been implemented.)

To test an email for confidentiality, run EmailTextGUI in **scanner.analysis**. Add in some text to test,
and press *Evaluate Email*. A score will be generated and displayed in the bottom right. Currently scores are
not very useful and tend to err towards 1.0, until we adjust the values to give better scores. There is an
*Upload File* button in this GUI as well, and again file selection has not been implemented yet.
Phrases are also not being checked for at this point.

Known Errors:
Currently running from the command line results in errors being thrown relating to missing dependencies.
