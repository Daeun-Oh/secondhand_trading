import os
import sys
import pickle
import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import KNeighborsClassifier

# if len(sys.argv) < 1:
#     sys.exit(1)

# port = sys.argv[0] if sys.argv[0] else "3000"

# 전처리
ss = StandardScaler()
kn = KNeighborsClassifier(p=1)


url = "http://localhost:" + "4000" + "/restaurant/train"
data = pd.read_json(url)

train_input = data[['lat', 'lon']].to_numpy()
train_target = data['seq'].to_numpy()

ss.fit(train_input)
train_scaled = ss.transform(train_input)

kn.fit(train_scaled, train_target)

# 모델 저장
with open("model.pkl", "wb") as f:
    pickle.dump(kn, f)

# 표준점수 변환기 저장
with open("scaler.pkl", "wb") as f:
    pickle.dump(ss, f)

# 타겟 저장
with open("target.pkl", "wb") as f:
    pickle.dump(train_target, f)
