from kahoot import KahootClient
from question import Choice, Question
from quiz import Quiz

client = KahootClient()

client.login('blmsyt', 'bulldogs')

quiz = Quiz('Title', 'Description')

quiz.add(
    Question([
        Choice('bob', True),
        Choice('fred', False),
        Choice('joe', False),
        Choice('sam', True)
    ], 'What is the biggest babu?', 30))

quiz.add(
    Question([
        Choice('bob', True),
        Choice('fred', False),
        Choice('joe', False),
        Choice('sam', True),
        Choice('asdf', False)
    ], 'What is the biggest babu?', 30))

client.upload_quiz(quiz)

client.close()
