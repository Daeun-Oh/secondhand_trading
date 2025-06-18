import pickle
import numpy as np
import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split, GridSearchCV
from sklearn.linear_model import SGDClassifier

df = pd.read_csv("diabetes_prediction_dataset.csv")

# -- 데이터 전처리 S --

# 1. 문자열 데이터를 숫자로 인코딩

"""
['gender']

Female  0
Male    1
Other   2

"""
df = df.replace(to_replace={'gender': {'Female': 0, 'Male': 1, 'Other': 2}})

"""
['smoking_history']

No Info         0
current         1
ever            2
former          3
never           4
not current     5
other           6

"""
df = df.replace(to_replace={'smoking_history': {'No Info': 0, 'current': 1, 'ever': 2, 'former': 3, 'never': 4, 'not current': 5, 'other': 6}})

# 2. 훈련 데이터, 정답(타겟) 데이터 분리

diabetes_target = df['diabetes'].to_numpy()
diabetes_data = df.drop('diabetes', axis=1).to_numpy()

# 3. 훈련 세트(전체 데이터의 90%), 테스트 세트(전체 데이터의 10%) 분리

train_input, test_input, train_target, test_target = train_test_split(
    diabetes_data, diabetes_target, test_size=0.1
)

# 4. 정규화 (표준 점수 변환)

ss = StandardScaler()   # 각 피처를 평균 0, 표준편차 1로 스케일링
ss.fit(train_input)     # 표준 점수의 기준은 무조건 훈련 세트
train_scaled = ss.transform(train_input)    # 무조건 fit과 같은 기준으로 변환
test_scaled = ss.transform(test_input)      # 무조건 fit과 같은 기준으로 변환

# -- 데이터 전처리 E --

# -- 모델 학습 및 최적화 S --

# SGDClassifier: 가장 좋은 모델 생성

params = {'max_iter': np.arange(90, 120)}
gs = GridSearchCV(SGDClassifier(loss='log_loss', n_jobs=-1, tol=None), params, n_jobs=-1)
gs.fit(train_scaled, train_target)  # 학습 필수!
model = gs.best_estimator_

# print("훈련 세트:", model.score(train_scaled, train_target))
# print("테스트 세트:", model.score(test_scaled, test_target))

# -- 모델 학습 및 최적화 E --

# 학습한 모델 저장
with open("model.pkl", "wb") as f:
    pickle.dump(model, f)

# 표준 점수 Scaler 저장
with open("scaler.pkl", "wb") as f:
    pickle.dump(ss, f)