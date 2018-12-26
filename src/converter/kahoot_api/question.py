class Choice:
    def __init__(self, name, is_correct):
        self.name = name
        self.is_correct = is_correct


class Question:
    def __init__(self, choices, question, time=10):
        self.choices = choices
        self.question = question
        self.time = time
        if len(self.choices) > 4:
            print('Question has more than 4 choices. Truncating to 4.')
            self.choices = self.choices[0:4]

    def to_dict(self, idx):
        choices = [{
            'answer': c.name,
            'correct': c.is_correct
        } for c in self.choices]
        q = {
            'choices': choices,
            'id': str(idx),
            'image': '',
            'imageMetadata': {},
            'numberOfAnswers': 4,
            'points': True,
            'question': self.question,
            'questionFormat': 0,
            'resources': '',
            'time': self.time * 1000,
            'type': 'quiz',
            'video': {}
        }

        return q
