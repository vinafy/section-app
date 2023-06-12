from flask import Flask

from news_recommendation_2 import predict_article

app = Flask(__name__)

@app.route('/api/<user_id>', methods=['GET'])
def hello(user_id):
    data = predict_article(user_id,10)
    return {
        'massage': 'top 10 recommendations article',
        'title': data
    } 

if __name__ == '__main__':
    app.run()

