class Quiz:
    def __init__(self, title, description):
        self.description = description
        self.title = title
        self.questions = []

    def add(self, question):
        self.questions.append(question)

    def to_dict(self):
        quiz = {
            'audience': 'School',
            'coverMetadata': {},
            'created': None,
            'description': self.description,
            'language': 'English',
            'lobby_video': {},
            'metadata': {},
            'organisation': None,
            'questions': [q.to_dict(i) for i, q in enumerate(self.questions)],
            'quizType': 'quiz',
            'resources': '',
            'themeId': None,
            'title': self.title,
            'type': 'quiz',
            'uuid': None,
            'visibility': 1
        }

        return quiz
