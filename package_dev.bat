set M2_HOME=D:\Program\apache-maven-3.0.4\
echo % M2_HOME%
call mvn clean:clean
call mvn package -Dmaven.test.skip=true -Pdev
@pause
