# ๐น ํด๋ก  ํ๋ก์ ํธ BackEnd ๐น

## ํ๋ก์ ํธ ์ค๋ช
๐ฎ ๋กค ์ ์  ๊ฒ์ OP.GG ํด๋ก  ๐ฎ


https://youtu.be/SxnBJpBXc_0

![20221103_143951](https://user-images.githubusercontent.com/111861625/199654656-3c1ca99b-6df5-479f-9a44-0efcb9ec9a6e.png)


<br>



## ๐ ์ฃผ์๊ธฐ๋ฅ
**1. ๊ฒ์๊ธ ์์ฑ**

**2. ์ธ๊ธฐ๊ธ ์กฐํ**

**3. ๊ฒ์๊ธ ๊ฒ์**

**4. ์ด๋ฉ์ผ ์ค๋ณต ํ์ธ**

**5. ๋๋ค์ ์ค๋ณต ํ์ธ**


<br>

## โ API ์ค๊ณ
https://www.notion.so/686e87c64771405f915dbcea50d3dd3a?v=9bcdea42e28a405b975f03742df511e2

## ๐ ERD
![Untitled (1)](https://user-images.githubusercontent.com/111861625/199657256-9fc249ce-94db-48de-a807-c2fc277602a5.png)


## ๐ ํธ๋ฌ๋ธ์ํ

<details>
<summary>1. ๊ฒ์ ๊ธฐ๋ฅ ๊ตฌํ ์ RequestParam์ด๋ธํ์ด์ ๋ฌธ์ 
</summary>
<div markdown="1">  
    
    ๐ ๊ฒ์ ๊ธฐ๋ฅ ๊ตฌํ์ ์ปจํธ๋กค๋ฌ์์ RequestParam์ด๋ธํ์ด์์ ์์ฑํ์ง ์์ response๋ฅผ ํ๋ก ํธ๋ก ๋ณด๋ผ์ ์์๋ ๋ฌธ์ ํด๊ฒฐ
      (๊ตฌ๊ธ๋ง ์ ์๋ต์ด ๊ฐ๋ฅํ๋ค๊ณ ๋ ๋ณด์์ผ๋ ํ์คํ์ง ์์ value์ required, defaultValue๋ฅผ ๋ชจ๋ ๋ช์ํด๋๊ธฐ๋ ํ์์)
    	    
</div>
</details>

<details>
<summary>2. ์ข์์๊ฐ ๋๋ฆฐ ๊ฒ์๊ธ์ ์ญ์ ๊ฐ ๋์ง ์๋ ๋ฌธ์ ๋ฐ์
</summary>
<div markdown="1">  
    
    ๐ post delete ์์ 
    
     LikesRepository ์
     void deleteAllByPost(Post post); ์ถ๊ฐ //LikesRepository์ ๊ฒ์๊ธ์์ ์ข์์๋ฅผ ์ฐพ์ ์ญ์ ํ๋ ๋ฉ์๋
    
     PostService delete ๋ถ๋ถ์
     likesRepository.deleteAllByPost(post); ์ถ๊ฐ //post delete ๋ฉ์๋ ์  like delete ๋ฉ์๋ ๊ธฐ๋ฅ ์ถ๊ฐ
          
	    
</div>
</details>

### BackEnd ํ์ ๊นํ๋ธ
๐ฉโ๐ป [๊ฐ์ง๊ตฌ](https://github.com/kang0906) ๐ฉโ๐ป [์กฐ์ ์ฐ](https://github.com/jjw0611) ๐จโ๐ป [์ด๋์ฌ](https://github.com/Pdongjaelee)
