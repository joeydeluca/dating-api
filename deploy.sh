gradle clean build || exit 1

rm artifact.zip

zip -r artifact.zip -j build/libs/dating-api-1.0.0.jar
zip -r artifact.zip .ebextensions Procfile

eb deploy --staged