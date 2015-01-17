#!/bin/bash
# subApp name should start with a char of lower case
NEW_SUBAPP=book
POM_BASE_DIR=/home/woody/git/so
cp "${POM_BASE_DIR}/src/java/info/woody/so/bean/CostBean.java"             "${POM_BASE_DIR}/src/java/info/woody/so/bean/${NEW_SUBAPP^}Bean.java"
cp "${POM_BASE_DIR}/src/java/info/woody/so/controller/CostController.java" "${POM_BASE_DIR}/src/java/info/woody/so/controller/${NEW_SUBAPP^}Controller.java"
cp "${POM_BASE_DIR}/src/resources/mybatis/mybatis-cost-mapper.xml"         "${POM_BASE_DIR}/src/resources/mybatis/mybatis-${NEW_SUBAPP}-mapper.xml"
cp "${POM_BASE_DIR}/WebContent/static/js/so/cost.js"                       "${POM_BASE_DIR}/WebContent/static/js/so/${NEW_SUBAPP}.js"
cp "${POM_BASE_DIR}/WebContent/WEB-INF/pages/inc/cost.jsp"                 "${POM_BASE_DIR}/WebContent/WEB-INF/pages/inc/${NEW_SUBAPP}.jsp"

NEW_CREATED_FILES=`cat <<HERE
${POM_BASE_DIR}/src/java/info/woody/so/bean/${NEW_SUBAPP^}Bean.java
${POM_BASE_DIR}/src/java/info/woody/so/controller/${NEW_SUBAPP^}Controller.java
${POM_BASE_DIR}/src/resources/mybatis/mybatis-${NEW_SUBAPP}-mapper.xml
${POM_BASE_DIR}/WebContent/static/js/so/${NEW_SUBAPP}.js
${POM_BASE_DIR}/WebContent/WEB-INF/pages/inc/${NEW_SUBAPP}.jsp
HERE`

for file in ${NEW_CREATED_FILES};
do
sed -i "s/cost/${NEW_SUBAPP}/g" ${file};
sed -i "s/Cost/${NEW_SUBAPP^}/g" ${file};
done;
