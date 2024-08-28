FROM nexus3.onap.org:10001/onap/org.onap.integration.nfsimulator.vesclient

ENV USE_CERTIFICATE_FOR_AUTHORIZATION=false
ENV STRICT_HOSTNAME_VERIFICATION: "false"
ENV MONGO_PORT: 27017

COPY ./properties/application.properties /app/application.properties

EXPOSE 5000
