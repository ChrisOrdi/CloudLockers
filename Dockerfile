FROM ubuntu:latest
LABEL authors="chrisvandalen"

ENTRYPOINT ["top", "-b"]