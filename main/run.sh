gradle clean shadowJar
cp config.properties build/libs/
java -jar build/libs/CoinGod.jar