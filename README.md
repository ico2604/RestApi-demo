
## 사용된 기술

- Spring Boot 3.2.3
- Java 17
- queryDSL
- MySQL
- jjwt + Spring Security

## API 문서 확인

API 문서는 Swagger를 이용하여 자동으로 생성되며, 아래 링크를 통해 확인할 수 있습니다:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)

## 빌드 및 실행

프로젝트를 빌드하고 실행하기 위해서는 다음의 단계를 따르십시오:

1. MySQL을 설치하고 설정합니다.
2. `application.properties` 파일에서 데이터베이스 연결 정보를 설정합니다.
3. 프로젝트를 빌드합니다.

## 로그인

API에 접근하기 위해서는 로그인이 필요합니다. 로그인은 jjwt와 Spring Security를 사용하여 구현되었습니다. 인증이 필요한 엔드포인트에 접근하면 토큰을 발급받아야 합니다.

## Ubuntu Redis 서버 셋팅
1. make
sudo apt install make

2. make 하기 위핸 gcc 다운
sudo apt install -y gcc

3. redis-cli 설치 및 make
wget http://download.redis.io/redis-stable.tar.gz && tar xvzf redis-stable.tar.gz && cd redis-stable && make

4. redis-cli를 bin에 추가해 어느 위치서든 사용 가능하게 등록
sudo cp src/redis-cli /usr/bin/

5. 연결
redis-cli -h 본인의 노드 엔드포인트 -p 6379

## GitHub Actions CI/CD (docker)

### CI/CD
   - **지속적 통합 (Continuous Integration, CI):**
     - CI는 코드 변경사항을 메인 브랜치에 통합하는 프로세스입니다. 코드가 통합될 때마다 자동으로 빌드 및 테스트가 수행되어, 오류나 문제점을 빠르게 발견하고 해결할 수 있습니다.
   - **지속적 배포 (Continuous Delivery/Deployment, CD):**
     - CI의 다음 단계입니다. 코드 변경 사항을 프로덕션 환경에 자동 배포합니다.

### CI (Continuous Integration)
깃허브에서 자동으로 추천을 해줍니다.

Actions -> Java with Gradle 생성

### CD (Continuous Deployment)
EC2에 직접 접근하여 docker 컨테이너를 띄우는 대신 GitHub Actions를 사용하여 자동화 배포를 진행할 예정입니다.

배포 과정:
1. AWS EC2에 Docker 설치하기:
    sudo apt update
    sudo apt install docker.io
    sudo apt install docker-compose
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -a -G docker $USER
2. SSH 키 등록:
    EC2를 생성할 때 받았던 pem 파일을 사용하여 GitHub 프로젝트 설정의 Secrets and variables > Actions > New repository secret에서 등록합니다.
3. Docker Hub Access Token 발급 받기:
    Docker Hub 사이트 > My Account > Security에서 New Access Token을 발급 받습니다. 발급 받은 토큰을 사용하여 docker login -u <username>을 수행합니다.
4. DOCKER_USERNAME, DOCKER_HUB_TOKEN 등록:
    EC2_SSH_PRIVATE_KEY를 등록한 것처럼 DOCKER_USERNAME과 DOCKER_HUB_TOKEN을 등록합니다.
5. application.yml 등록:
    민감 정보를 관리하기 위해 gitignore에 등록된 application.yml을 New repository secret에 등록합니다.
6. Dockerfile 작성
7. 배포를 위한 .github/workflows/gradle.yml 작성
8. 해당 서버에 접속하여 git clone github.com/ico2604/project-name
9. 깃이 수정되었을 경우 git pull origin main 먼저 실행
10. 도커 로그인 docker login -u ID -p PW
11. docker build -t 이미지명 .
12. docker run -d -p 8080:8080 이미지명:latest


