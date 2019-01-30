
clone the argus-gui for change, if not change, just keep the src/main/resources/public folder
if you want change the gui, you need change it in the argus-gui project and then gulp build, after build
copy all the file under dist to this project 's src/main/resources/public.


#build docker　
mvn clean package docker:build


#build docker and push

　mvn clean package docker:build -DpushImage