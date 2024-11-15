# inspectit-ocelot-dev-com.example.demo
Demo application for local development. 

Currently, this project uses the following versions:
- inspectit-ocelot-agent & configuration-server: 2.6.6
- EUM-server: 2.6.1
- Demo Spring version: 3.2.0

---
## inspectit-ocelot

This folder contains recent releases of the
- inspectit-ocelot-agent
- inspectit-ocelot-configuration-server
- inspectit-ocelot-eum-server

## docker

This folder contains a `docker-compose.yml` file, which starts the following containers:
- OpenTelemetry-Collector (Receive metrics & traces)
- Jaeger (Receive & view traces)
- InfluxDB (Receive metrics)
- Grafana (View metrics)

## com.example.demo

The demo resembles an ordinary spring boot project. It provides services, REST-controllers as well as HTML files
for the web-UI.

---
## How-To-Run

### inspectit-ocelot

Start the configuration-server via `java -jar inspectit-ocelot-configurationserver-2.6.6.jar`.
You can reach the UI on port 8090.

Start the EUM-server via `java -jar inspectit-ocelot-eum-server.2.6.1.jar`
The server should automatically apply the [application.yml](application.yml), 
which enables the influx-exporter for metrics and the OTLP-exporter for traces.
The EUM-server also provides the [boomerang-opentelemetry-plugin](https://github.com/NovatecConsulting/boomerang-opentelemetry-plugin).

To start the ocelot-agent please view the demo-section.

### Docker

Start the docker container via `docker compose up` in the `/docker` folder.
You can open the UI of some containers on the following ports:
- Jaeger: 16686
- Grafana: 3030

### Demo

You can start the demo application without an attached ocelot agent just like every other spring application.

To start the application with an attached ocelot agent, you have to:
- build a demo.jar via `gradle assemble`
- start the demo via `java -javaagent:"<absolute-path-to-your-project>\inspectit-ocelot-dev-demo\inspectit-ocelot\inspectit-ocelot-agent-2.6.6.jar" -Dinspectit.service-name=my-demo-agent -Dinspectit.config.http.url=http://localhost:8090/api/v1/agent/configuration -jar <absolute-path-to-your-project>\inspectit-ocelot-dev-demo\build\libs\com.example.demo-0.0.1-SNAPSHOT.jar `

You can reach the main-page at http://localhost:8081

To collect traces, open the http://localhost:8081/greeting.
This page does load the boomerang-opentelemetry-plugin, collects traces and sends them to the EUM-server.
You can also create traces by clicking on the 'Change picture' button.
This button should change the colour of the displayed ocelot.

If you experience a lacking web-UI, please ensure to install all node_modules for the frontend.
You can install the node_modules via: `yarn install --modules-folder src/main/resources/static` in the `/demo` folder.
Please ensure you have installed `yarn`.

See the [debugging docu](Debugging.md) to learn, how to debug the inspectit-ocelot agent locally.

