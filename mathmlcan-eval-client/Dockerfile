FROM node:12 as dependencies

WORKDIR /app-deps

COPY package.json .

RUN npm install


FROM node:12 as build
WORKDIR /app-build

COPY --from=dependencies /app-deps/ .

COPY . .

RUN npm run build


FROM nginx:1.18.0-alpine

COPY nginx.conf /etc/nginx/nginx.conf

WORKDIR /usr/share/nginx/html


COPY --from=build /app-build/dist/mathmlcan-eval-client .
