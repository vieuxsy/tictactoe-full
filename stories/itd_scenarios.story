Meta:

Narrative:
As a web user
I want to search websites by keyword
So that I can read their content

Scenario: IT-Designers search on wiki
Given user browses to http://en.wikipedia.org/wiki/Main_Page
When user enters IT-Designers in input with name search
When user clicks button with name go
Then website title starts with IT-Designers