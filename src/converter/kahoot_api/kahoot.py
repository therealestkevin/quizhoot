import json

import requests


class KahootClient:
    _login_url = 'https://create.kahoot.it/rest/authenticate'
    _quiz_url = 'https://create.kahoot.it/rest/kahoots'
    _json_header = {'content-type': 'application/json'}

    def __init__(self):
        self._token = None
        self._session = None

    def login(self, username, password):
        self._session = requests.session()
        payload = {
            'username': username,
            'password': password,
            'grant_type': 'password'
        }

        result = self._session.post(
            KahootClient._login_url,
            data=json.dumps(payload),
            headers=KahootClient._json_header)
        self._token = result.json()['access_token']

    def upload_quiz(self, quiz):
        headers = {'authorization': self._token}
        result = self._session.post(
            KahootClient._quiz_url,
            data=json.dumps(quiz.to_dict()),
            headers={
                **KahootClient._json_header,
                **headers
            })
        # print(result.text)

    def close(self):
        self._session.close()
