from flask import Flask, request, jsonify, make_response, send_from_directory
import requests
from flask_cors import CORS

app = Flask(__name__, static_folder='static')
CORS(app, resources={r"/*": {"origins": "*"}})

API_BASE_URL = 'https://todo.doczilla.pro/api'

@app.route('/')
def home():
    return send_from_directory(app.static_folder, 'index.html')

@app.route('/todos', methods=['GET'])
def get_todos():
    params = request.args
    response = requests.get(f"{API_BASE_URL}/todos", params=params)
    return jsonify(response.json())

@app.route('/todos/date', methods=['GET'])
def get_todos_by_date():
    params = request.args
    response = requests.get(f"{API_BASE_URL}/todos/date", params=params)
    return jsonify(response.json())

@app.route('/todos/find', methods=['GET'])
def find_todos():
    params = request.args
    response = requests.get(f"{API_BASE_URL}/todos/find", params=params)
    return jsonify(response.json())

if __name__ == '__main__':
    app.run(debug=True)