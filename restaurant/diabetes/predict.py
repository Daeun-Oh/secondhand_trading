import os
import sys
import json
import pickle
import numpy as np
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import SGDClassifier

# -- 입력 데이터 처리 S --
# 예: python predict.py '[[0.0, 13.0, 0.0, 0.0, 0.0, 24.11, 5.0, 140.0]]
if len(sys.argv) < 2:
    sys.exit(1)

try:
    data_json = sys.argv[1]
    data = np.array(json.loads(data_json))
except Exception as e:
    print("입력 데이터 형식 오류:", e)
    sys.exit(1)

# -- 입력 데이터 처리 E --

# 기준 경로
base_path = os.path.dirname(os.path.realpath(__file__)) + "/"  # predict.py 속한 폴더(/diabetes)의 절대경로

# 모델과 Scaler 불러오기
with open(base_path + "model.pkl", "rb") as f:
    model = pickle.load(f)

with open(base_path + "scaler.pkl", "rb") as f:
    scaler = pickle.load(f)

# 모델 예측
data_scaled = scaler.transform(data)
predictions = model.predict(data_scaled)
print(json.dumps(predictions.tolist()))