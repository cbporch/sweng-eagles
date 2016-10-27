# sweng-eagles

All dependencies should be pulled by Maven when you first run anything.
Code requires Java 1.8

To enter confidential words into the test database, run DatabaseInput.java, in scanner.dbEntry. Currently,
as of 10.26.16, you can enter one word and one phrase at a time, whether or not you want synonyms
added as well, whether the existence of a number adjacent to that word makes it more confidential
and the probability that a word is confidential. There is also a button to select a file to upload,
but that currently is non-functional. (Code has been written to upload .csv files, but file
selection has not been implemented.)


To test an email for confidentiality, run EmailTextGUI in scanner.analysis. Add in some text to test,
and press Evaluate Email. There is an Upload File button in this GUI as well, and again file selection
has not been implemented yet.