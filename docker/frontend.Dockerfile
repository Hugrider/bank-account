# Étape 1 : Build Angular
FROM node:20-alpine AS build
WORKDIR /app
COPY . .
RUN npm ci && npm run build -- --configuration production

# Étape 2 : Serveur Nginx
FROM nginx:1.27-alpine
COPY --from=build /app/dist/bankAccount-ihm/browser /usr/share/nginx/html

# Copie du fichier de config Nginx si besoin
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80