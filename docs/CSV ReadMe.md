**CSV File Format**

The format of a CSV file should have one word or phrase on every line followed by the values of that word.

For a single word, it should be input in the following format:

The word itself, a number that represents how secure that word is, and then whether or not the word contains numbers that make it more secure based on the numbers.

An example of a single word is the word &quot;Rowan&quot; with the secure value of .4 and no important numbers following it. So we would enter this into the file as shown below.

Rowan, .4, 0

If there was another word after that, we would input it like this,

Rowan, .4, 0,

University, .8, 0

This time, we added a comma after the rowan line because there was another word following it. An example of a word with extra secureness based on numbers, would be &quot;1000Rocks&quot; with the secure value of .7 and this time there are secure numbers after it. This would be shown like this:

1000Rocks, .7, 1

You will notice that there is a 1 for the last value now. This is because it used secure numbers. If we have all of these words in the file, it would look like this:

Rowan, .4, 0,

University, .8, 0,

Rocks138, .7, 1

For a phrase, it&#39;s the same thing except there is a place for the number of words in the format. You type the number of words right after the phrase itself. So for the phrase &quot;Eagles are better than tigers&quot; it would be input as follows:

Eagles are better than tigers, 5, .8, 0

This phrase had 5 words, a secureness of .8, and no secure numbers added to it.

The phrase &quot;2 heads are better than 1&quot; with a secureness of .2, and the numbers are important, it would look like this:

2 heads are better than 1, 4, .2, 1





If we added everything in one file, it could look like this:

Rowan, .4, 0,

University, .8, 0,

2 heads are better than 1, 4, .2, 1,

Rocks138, .7, 1,

Eagles are better than tigers, 5, .8, 0



**Restrictions**

No commas allowed in words that are input. For obvious reasons, this will make the file be interpreted in the wrong way and will not work correctly.
