FROM adoptopenjdk:11

ADD ./app/build/distributions/app.tar /
COPY ./openapi.yaml /app/public/

WORKDIR /app

EXPOSE 8100

CMD ./bin/app -c $VK_CLIENT_ID -s $VK_SECRET -r $VK_REDIRECT_URL
