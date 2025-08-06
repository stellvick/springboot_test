# Multi-stage build para otimizar o tamanho da imagem
FROM gradle:8.10-jdk17-alpine AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração do projeto
COPY build.gradle settings.gradle ./

# Fazer download das dependências (para cache)
RUN gradle dependencies --no-daemon || true

# Copiar código fonte
COPY src src

# Fazer build da aplicação usando o gradle da imagem
RUN gradle clean build -x test --no-daemon

# Estágio final - imagem de produção
FROM openjdk:17-jdk-alpine

# Instalar dumb-init e curl
RUN apk add --no-cache dumb-init curl

# Criar usuário não-root para segurança
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR da build
COPY --from=build /app/build/libs/*.jar app.jar

# Mudar ownership do diretório para o usuário da aplicação
RUN chown -R appuser:appgroup /app

# Mudar para o usuário não-root
USER appuser

# Expor a porta da aplicação
EXPOSE 8080

# Definir variáveis de ambiente padrão
ENV JAVA_OPTS="-Xms256m -Xmx512m" \
    SPRING_PROFILES_ACTIVE=prod

# Usar dumb-init como entrypoint para melhor handling de sinais
ENTRYPOINT ["dumb-init", "--"]

# Comando para executar a aplicação
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
