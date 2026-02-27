import sys
import os
from datetime import datetime
from crawler import Crawler
from notice import *

"""
공지 크롤러 실행 스크립트.

이 모듈은 CLI 인자를 기반으로 특정 게시판의 공지를 수집한 뒤
수집된 데이터를 Backend(SpringBoot) API로 전송

환경변수:
    API_URL: 공지를 전송할 Backend API URL
    NOTICE_CNT: 공지사항 크롤링 개수
    RECRUITING_CNT: 학부인재모집 크롤링 개수
    EMPLOYMENT_CNT: 취업정보 크롤링 개수

사용 예:
    python main.py -Notice
    python main.py -Recruiting
    python main.py -Employment
"""

crawler = Crawler()
url = os.environ['API_URL']
noticeCnt = os.environ['NOTICE_CNT']
recruitingCnt = os.environ['RECRUITING_CNT']
employmentCnt = os.environ['EMPLOYMENT_CNT']

def run(typeSelect: str):
    """
    선택한 게시판 유형에 따라 공지를 크롤링하고 Backend로 전송한다.

    Args:
        typeSelect (str): 실행 옵션
            - '-Notice'
            - '-Recruiting'
            - '-Employment'

    동작 흐름:
        1. 게시판 유형에 맞는 공지 개수 설정
        2. Crawler를 통해 공지 수집
        3. Backend API로 전송
    """
    #
    # Start Function
    # 
    # Parameter)
    #   typeSelect: 가져올 공지 종류 선택 (공지사항 / 학부인재모집 / 취업정보)
    #    
    match (typeSelect):
        case '-Notice':
            print(f'\n{datetime.now()} - Start crawling ({typeSelect[1:]})')
            noticeList = crawler.get_all_notice(type='공지사항', noticeCnt=int(noticeCnt))
        case '-Recruiting':
            print(f'\n{datetime.now()} - Start crawling ({typeSelect[1:]})')
            noticeList = crawler.get_all_notice(type='학부인재모집', noticeCnt=int(recruitingCnt))
        case '-Employment':
            print(f'\n{datetime.now()} - Start crawling ({typeSelect[1:]})')
            noticeList = crawler.get_all_notice(type='취업정보', noticeCnt=int(employmentCnt))
        case _:
            print("Usage:", sys.argv[0], "-[ Notice | Recruiting | Employment ]\n")
            return
    print(f'{datetime.now()} - Finish crawling ({typeSelect[1:]})')
    response = crawler.send_notice_to_api(url, noticeList)
    print(f'{datetime.now()} - Finish sending ({typeSelect[1:]}) - {response}')

if __name__ == '__main__':
    if (len(sys.argv) != 2):
        print("Usage:", sys.argv[0], "-[ Notice | Recruiting | Employment ]\n")
    else:
        run(sys.argv[1])

