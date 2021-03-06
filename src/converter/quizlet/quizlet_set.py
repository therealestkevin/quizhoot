import json


class QuizletSet:
    def __init__(self,
                 terms,
                 title='Quizlet',
                 description='Autogenerated by Quizhoot'):
        self.terms = terms
        self.title = title
        self.description = description

    def write_terms_json(self, path):
        dictionary = {'array': self.terms}
        with open(path, 'w+') as f:
            json.dump(dictionary, f, ensure_ascii=False)
