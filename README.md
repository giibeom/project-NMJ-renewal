# 놀(고) 먹(고) 자(고) - Renewal

### 개요
처음 개발을 배운 IT 학원에서 팀장을 맡으며 5명의 팀원들과 함께 5주동안 개발했던 놀먹자라는 자리예약 서비스 프로젝트입니다. <br>
2년 간의 실무 경험과, 최근 학습한 JPA와 TestCode를 적용해보며 정리하기 위해 기존 프로젝트를 새로 만들어봅니다.
- 기존 프로젝트: https://github.com/giibeom/project-NMJ 


<br>

### 프로젝트 리뷰

#### [백기선님](https://github.com/whiteship) 라이브 코드 리뷰
- 피드백 영상 (기선님 허락 받고 편집하였습니다)
  - https://youtu.be/aB6KyRO6l2w
- 관련 회고
  - [백기선님을 만나 뵈다 | Feat. 이분이라면 같이 일할만하죠 충분히...](https://velog.io/@beomdrive/%EB%B0%B1%EA%B8%B0%EC%84%A0-%EC%84%A0%EC%9E%A5%EB%8B%98%EC%97%90%EA%B2%8C-%EB%A6%AC%EB%B7%B0%EB%A5%BC-%EB%B0%9B%EC%95%84%EB%B3%B4%EB%A9%B0-feat.-%EC%98%AC%ED%95%B4-%EB%B3%B5-%EB%8B%A4-%EB%B0%9B%EC%95%98%EB%8B%A4)

<br>

### Github 전략 (Git Flow)


#### Branch 구조
```markdown
|-- master
|   |-- hotfix
|   |   |-- issue-#885-user-deleted-error
|-- develop
|   |-- feat
|   |   |-- issue-#883-user-update
|   |   |-- issue-#884-user-delete
|   |-- refactor
|   |   |-- issue-#887-user-refactoring
```

#### Branch merge
> merge 시 커밋 scope에는 `PR 번호`를 넣습니다.

- `feat/refactor -> develop` : **Squash and Merge**
  - merge commit message => `feat(#2): 사용자 등록 기능을 추가한다`
- `hotfix -> main` : **Squash and Merge**
  - merge commit message => `fix(#2): 예약 시 매장 회원에게 승인 요청이 안가는 오류를 수정한다`
- `develop -> main` : **Rebase and Merge**
  - merge commit message => `merge(#2): 백엔드 프로젝트를 생성한다`

#### Commit Convention
> 로컬에서의 커밋 scope에는 `Issue 번호`를 넣습니다. 

|    타입    |                 설명                 |                    예시                     |
|:--------:|:----------------------------------:|:-----------------------------------------:|
|  merge   |              PR merge              |         merge(#2): 백엔드 프로젝트를 생성한다         |
|   feat   |             새로운 기능 추가              |         feat(#3): 사용자 등록 기능을 추가한다         |
|   fix    |               버그 수정                | fix(#3): 예약 시 매장 회원에게 승인 요청이 안가는 오류를 수정한다 |
| refactor |              코드 리팩토링               |    refactor(#3): 예약 비즈니스 로직 리팩토링을 진행한다    |
|   test   |             테스트 코드 작성              |         test(#3): 예약 실패 케이스를 추가한다         |
|   docs   |               문서 수정                |         docs(#3): README에 ERD를 추가한다         |
|  chore   | 빌드 업무, 패키지 매니저 수정 등 (소스 코드 외적인 작업) |            chore: PR 템플릿을 생성한다            |


<br>

### 데이터베이스 ERD
- https://www.erdcloud.com/d/uykGfNjFfDtZDvzpk


#### 1차 스프린트

<img src="images/DB-ERD-Sprint1.png" width="100%" />
