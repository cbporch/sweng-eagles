# sweng-eagles


All dependencies should be pulled by Maven when you first run anything.

*Code requires Java 1.8*
*Building code is made much easier with Maven*

If you package the project in the command line using Maven like so:

`mvn package`

it should generate a sweng-eagles-1.0.jar file in the target directory that should open the Email text checking window.


To enter confidential words into the test database, run **DatabaseInput.java**, in scanner.dbEntry, (or run the
sweng-eagles-1.0.jar to open both). Currently,
as of 10.27.16, you can enter one word and one phrase at a time, there is a radio button to mark whether or not you want
synonyms added, a radio button to mark whether the existence of a number adjacent to that word makes it
more confidential (# Dependent), and the probability that a word is confidential. There is also a button to select
a CSV file to upload. The probability field defaults to 1.0.

(The number dependent radio button stores a value in the database, but is currently not used in score processing.)
(Code has been written to upload .csv files, *and now been implemented.* To read more about the CSV file structure, read the **CSV-readme** in the **docs/** directory)

To test an email for confidentiality, run
**sweng-eagles-1.0.jar**. Add in some text to test,
and press *Evaluate Email*. A score will be generated and displayed in the bottom right. 
~~Currently scores are
not very useful and tend to err towards 1.0, until we adjust the values to give better scores.~~ Current scores are the greater of either a generated score using Bayes Law or the largest probability for a confidential term in the text. There is an
*Upload File* button in this GUI as well, ~~and again file selection has not been implemented yet.~~ which has now been implemented.
~~Phrases are also not being checked for at this point.~~ There is now a new button at the bottom to enter words/phrases to the database. You will be presented with a login screen, current username is **'admin'** and the password is **'asrcSw3ng'**.

Algorithm Training:

Currently the training emails are encrypted using a shared AES encryption key.

Performance:

~~Currently performance needs improvement. A test set of lorem ipsum of 521 words take ~ 3 minutes to process.~~
As of 12-2-16, a test of 300 words of English language filler text (an exerpt from Kafka's Metamorphosis) now takes around 5-7 seconds, depending on the system.

Known Errors:
Leaving the login screen then re-entering causes auto-fill issues in the text boxes.

Sometimes the login window popup will not properly display the username and password text boxes. They are there and can be tabbed into, but display incorrectly.
