import json
import subprocess
import sys

import quizlet
from kahoot_api import Choice, KahootClient, Question, Quiz


def generate(config):
    gen_config = {
        'termsPath': config['termsPath'],
        'outPath': config['genOutPath'],
        'options': {
            'reverse': config['genOptions']['reverse'],
            'natLang': config['genOptions']['natLang']
        }
    }
    with open(config['genInPath'], 'w+') as f:
        f.write(json.dumps(gen_config))

    babu_call = 'java -cp ./build/generator.jar Babu {}'.format(
        config['genInPath'])
    subprocess.call(babu_call.split(' '))


if __name__ == "__main__":

    if len(sys.argv) < 2:
        print("Expected path to config file")
        sys.exit(1)

    with open(sys.argv[1]) as f:
        config = json.load(f)

    url = config['url']

    qset = quizlet.get_set(url)
    qset.write_terms_json(config['termsPath'])

    generate(config)

    with open(config['genOutPath']) as f:
        generated = json.load(f)

    client = KahootClient()

    client.login(config['username'], config['password'])

    quiz = Quiz(qset.title, qset.description)

    for term in generated:
        choices = [ Choice(c['def'], c['correct']) for c in term['choices'] ]
        quiz.add(Question(choices, term['word']))

    client.upload_quiz(quiz)

    client.close()
