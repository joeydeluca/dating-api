gradle clean build

zip -r artifact.zip -j build/libs/dating-api-1.0.0.jar
zip -r artifact.zip .ebextensions Procfile

eb deploy --staged