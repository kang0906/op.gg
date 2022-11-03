# 🕹 클론 프로젝트 BackEnd 🕹

## 프로젝트 설명
🎮 롤 전적 검색 OP.GG 클론 🎮


https://youtu.be/SxnBJpBXc_0

![20221103_143951](https://user-images.githubusercontent.com/111861625/199654656-3c1ca99b-6df5-479f-9a44-0efcb9ec9a6e.png)


<br>



## 🌟 주요기능
**1. 게시글 생성**

**2. 인기글 조회**

**3. 게시글 검색**

**4. 이메일 중복 확인**

**5. 닉네임 중복 확인**


<br>

## ⚙ API 설계
https://www.notion.so/686e87c64771405f915dbcea50d3dd3a?v=9bcdea42e28a405b975f03742df511e2

## 🔐 ERD
![Untitled (1)](https://user-images.githubusercontent.com/111861625/199657256-9fc249ce-94db-48de-a807-c2fc277602a5.png)


## 🚀 트러블슈팅

<details>
<summary>1. 좋아요가 눌린 게시글은 삭제가 되지 않는 문제발생
</summary>
<div markdown="1">  
    
    👌 post delete 수정

     PostService delete 부분에
     likesRepository.deleteAllByPost(post); 추가

     LikesRepository 에
     void deleteAllByPost(Post post); 추가
	    
</div>
</details>


### FrontEnd 팀원 깃허브
👩‍💻 [김성욱](https://github.com/) 👩‍💻 [김민석](https://github.com/) 

### BackEnd 팀원 깃허브
👩‍💻 [강진구](https://github.com/kang0906) 👩‍💻 [조정우](https://github.com/jjw0611) 👨‍💻 [이동재](https://github.com/Pdongjaelee)
