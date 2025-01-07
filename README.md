# example-spring-multi-services-monitoring-grafana
![image](https://github.com/user-attachments/assets/1084f12a-b75a-4dc5-8b67-320622d7e7b3)


### tech stack:
- java: app-svc (alpha-beta-gamma) ✔️
- prometheus: receiver log services ✔️
- open telemetry: collector trace and log ✔️
- grafana-tempo: managing trace ✔️
- grafana-loki: managing logs with trace ✔️
- grafana-grafana: dashboard and alerts ✔️


## how to run 
- need install first docker, docker compose, java 17
- run 'docker compose up'
- build `common` project
- run ':bootRun' for java services
  - alpha-svc :8081
  - beta-svc :8082
  - gamma-svc :8083
- test call api /hello on port 8081

## Note
- grafana port is 3000
- postman collection can be import from file `multi-svc.postman_collection.json`
- all scenario flow: user -> alpha-svc -> beta-svc ->gamma-svc


## Screenshot

### Case 1 : all service no error (user -> alpha-svc (ok) -> beta-svc (ok) -> gamma-svc (ok))
![multi-all-green](https://github.com/user-attachments/assets/b6fac428-3196-4322-bbc3-3b6c6e980502)

### Case 2 : gamma-svc response error (user -> alpha-svc (ok) -> beta-svc (ok) -> gamma-svc (response error) )
![multi-gamma-error](https://github.com/user-attachments/assets/f97f6b7d-3e10-4e5f-8049-999e3247b400)

### Case 3 : beta-svc response error (user -> alpha-svc (ok) -> beta-svc (response error) -> gamma-svc (ok))
![multi-beta-error](https://github.com/user-attachments/assets/fbfe2351-a2b7-4dce-8811-f06866fab621)

### Case 4 : alpha-svc response error (user -> alpha-svc (response error) -> beta-svc (ok) -> gamma-svc (ok))
![multi-alpha-error](https://github.com/user-attachments/assets/f6b41098-6e49-4079-8e42-90954c46f124)

