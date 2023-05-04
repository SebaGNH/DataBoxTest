# Stage 1 - the builder
FROM public.ecr.aws/bitnami/node:16 as build-deps

ARG CODEARTIFACT_AUTH_TOKEN
ARG SONAR_TOKEN
ARG Organization
ARG Project

WORKDIR /usr/src/app
COPY . ./
RUN npm i react-dmn --legacy-peer-deps
RUN npm i --force
RUN npm run build
RUN npm run test
RUN npm run sonarqube -Dsonar.login=$SONAR_TOKEN

# Stage 2 - the runner
FROM public.ecr.aws/nginx/nginx:stable
COPY ./rootfs/ /
COPY --from=build-deps /usr/src/app/build /usr/share/nginx/html
COPY entrypoint.sh .
RUN chmod +x entrypoint.sh
EXPOSE 80
ENTRYPOINT ["./entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]