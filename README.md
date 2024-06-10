## 📍 시나리오
1. 로컬 개발 (IntelliJ)
- 로컬 개발 환경(IntelliJ)을 사용하여 코드를 작성하고 변경 사항을 로컬 Git 저장소에 Commit 및 Push합니다.
2. GitHub Webhook 
- 코드가 GitHub에 Pusg되면, 설정된 Webhook이 Jenkins 서버로 알림을 보냅니다.
- Webhook은 Jenkins에게 새 코드 변경 사항이 있으므로 빌드를 시작하라고 알립니다.
3. Jenkins Build 시작
- Jenkins 서버는 Webhook을 통해 트리거된 후 빌드 파이프라인을 시작합니다.
- Jenkins는 설정된 Jenkinsfile을 기반으로 빌드 단계를 수행합니다.
4. Docker Image 빌드
- 빌드가 완료되면 Jenkins는 Docker를 사용하여 JAR 파일을 포함하는 Docker 이미지를 빌드합니다.
- docker build -t <image-name> . 명령을 통해 Docker 이미지를 생성합니다.
5. Docker Hub Image Push
- 생성된 Docker 이미지는 Docker Hub에 Push됩니다.
- docker push <image-name> 명령을 통해 이미지를 Docker Hub 저장소에 업로드합니다.
6. 배포 서버에서 Docker Image Pull
- 다운로드된 Docker 이미지는 배포 서버에서 컨테이너로 실행됩니다.
- docker run -d -p 8090:8090 --name springboot-app <image-name> 명령을 통해 컨테이너를 실행하고, Spring Boot 애플리케이션을 배포합니다.

## 📍 겪은 문제
🔹 Docker 컨테이너의 포트 할당 문제
- 발생 원인: Jenkins 파이프라인에서 Docker 컨테이너를 실행할 때 포트 8090이 이미 할당된 상태에서 새로운 컨테이너를 실행하려고 해서 발생.
- 해결 방법: 기존에 실행 중인 컨테이너를 중지하고 제거한 후에 새로운 컨테이너를 실행. 파이프라인 스크립트에서 docker stop과 docker rm 명령어를 추가하여 실행 중인 컨테이너를 처리.
  
🔹 gradlew 파일 실행 권한 문제
- 발생 원인: Jenkins 파이프라인에서 ./gradlew clean build 명령을 실행할 때 권한이 없어서 발생.
- 해결 방법: 로컬 개발 환경에서 gradlew 파일에 실행 권한을 부여 (chmod +x gradlew), GitHub에 Pusg하여 Jenkins에서 권한 문제를 해결.
  
🔹 EC2 인스턴스 메모리 부족 문제
- 발생 원인: EC2 인스턴스(프리티어)의 메모리 부족으로 인해 빌드 과정에서 성능 저하 및 실패 발생.
- 해결 방법: Swap 파일을 설정하여 가상 메모리를 추가. /etc/fstab 파일에 Swap 파일을 등록하고 활성화.

## 📍 아키텍쳐
![image](https://github.com/JaeYooooon/jenkinsTest/assets/99658884/4cf9f322-2436-475b-85d7-9562bbb186fb)
