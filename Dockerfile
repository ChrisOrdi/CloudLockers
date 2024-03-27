FROM ubuntu:latest
LABEL authors="chrisvandalen"

ENTRYPOINT ["top", "-b"]

# Stel de werkdirectory in de container in
WORKDIR /app


# Kopieer je uitvoerbare JAR-bestand naar de werkdirectory in de container
# Vervang 'app.jar' met de naam van je eigen JAR-bestand
COPY ./target/app.jar /app/app.jar

# De poort die door de server wordt gebruikt, openstellen
# Vervang '5101' met de poort die jouw app gebruikt
EXPOSE 5101

# Definieer de opdracht om de app uit te voeren
CMD ["java", "-jar", "app.jar"]
