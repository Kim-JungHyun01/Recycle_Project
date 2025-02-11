from function import *
import logging #로그
logging.basicConfig(level=logging.INFO)

from fastapi import FastAPI, UploadFile, File
app = FastAPI()

#적용할 모델 이름
model_name = "tmp05"
model = "model/pt/" + model_name + ".pt"

# 메인화면이동
@app.get("/") # get방식의 요청 테스트용 메세지를 json 형식으로 반환
async def read_root():
    return {"message" : "Python Server"}

# 이미지 받기
@app.post("/image_service", response_model=DetectionResult)
async def image_service(file:UploadFile = File(...)):
    # 결과 임시저장
    image_result_prev ={"prev": []}

    # 이미지를 읽어서 PIL 이미지로 반환
    image = Image.open(io.BytesIO(await file.read()))

    # 알파채널(A_필터)이 있다면 제거하고 RGB로 변환
    image = removal_filter(image)

    # custom 모델 적용
    results, class_names = model_predict_pt(model,image) #모델 예측
    # logging.info("result : ",results)

    # 결과 반환(class_name)
    result_class_name = print_json_pt(results, class_names)     #예측결과반환
    image_result_prev["prev"].append(result_class_name)         #예측결과 리스트추가
    result_json = remove_result_duplicate(image_result_prev)    #중복제거

    return DetectionResult(json_data=result_json)

#비디오 처리
@app.post("/video_service", response_model=DetectionResult)
async def video_service(video_file:UploadFile = File(...)):
    # 결과 json 저장
    frame_result_prev = {"prev": []}

    # 동영상 전달여부
    try:
        cap = cv2.VideoCapture(video_file)
        print("영상 전달완료")
    except:
        print("영상 전달실패")
        return

    while True:
        ret, frame = cap.read()

        if not ret:
            print("비디오 읽기 오류")
            break

        # cv2.waitKey(20) #캡처 딜레이

        # frame 캡쳐 갯수 조정
        if int(cap.get(1)) % 10 == 0:
            # print("frame number : ", str(int(cap.get(1))))

            # 모델 적용
            results, class_names = model_predict_pt(model, frame)

            # 결과 반환(class_name)
            result_class_name = print_json_pt(results, class_names) #예측결과 json
            # print(type(result_class_name))
            # print(result_class_name)
            frame_result_prev["prev"].append(result_class_name)     #예측결과 추가

    cap.release()  # 메모리해제
    cv2.destroyAllWindows()

    # 결과 중복제거
    result_json = remove_result_duplicate(frame_result_prev)
    return DetectionResult(json_data=result_json)



########################################################################
# 웹 실행_수동실행
if __name__ == "__main__": # uvicorn main:app인 경우 포트와 uvicorn 실행
    import uvicorn
    # 모든접근 허용 : host="0.0.0.0"
    uvicorn.run(app, host="0.0.0.0", port=8001)
    # uvicorn.run(app, host=ALLOWED_HOSTS, port=8001)
########################################################################