# Spring boot API Demo Application integration with Jenkins/Openshift/Helm/Docker
![](https://www.grape.solutions/static/fdaa06e0ed083acefd8fdf3ffaa0eba5/22bfc/logo_springboot.webp) ![](https://alternative.me/media/256/openshift-icon-ggcdtcwm5g3svcon-c.png) ![](https://www.grape.solutions/static/2e9698cec0951428338ae2e09d5ae31e/22bfc/logo_docker.webp) ![](https://www.grape.solutions/static/eccd657cf639107f983fc8e1fd65df46/22bfc/logo_jenkins.webp) ![](https://images.crunchbase.com/image/upload/c_pad,h_170,w_170,f_auto,b_white,q_auto:eco,dpr_1/nexfmbpjgbgdyzn4cmyv) ![](https://res.cloudinary.com/canonical/image/fetch/f_auto,q_auto,fl_sanitize,w_60,h_60/https://dashboard.snapcraft.io/site_media/appmedia/2017/06/helm.png)

This application is a demo spring boot application that consist of GET API call returns helloworld.

## Prerequisite Requirements
- Git repository
- Maven install (v.3.6.3)
- Java (Java 8)
- Docker / Podman / Skopeo (for image pull & push)
- Openshift / Kubernetes Server
- Jenkins Server

## Build Application:
- Jenkins:
  Configure the Cloud settings and set it to your k8s/Ocp.
  Create your service account and linked it with the namespace
  `oc policy add-role-to-user edit system:serviceaccount:[namespace]:[serviceaccount] -n [namespace]`
  
  Create a pipeline with a scripted pipeline and pull from the repository.
  

the following is the call sequence 
`demoapplication/applicationendpoint/helloworld`

port exposed: `8080`

## Build Sample
![Jenkins](https://i.ibb.co/1KDY0YR/Screenshot-2024-04-18-160429.png)
![Jenkins Build Pipeline](https://i.ibb.co/WGgPCfM/Screenshot-2024-04-18-161737.png)
![Openshift Image Stream](https://i.ibb.co/VvNNkJc/Screenshot-2024-04-18-162002.png)

## Deployment Sample
![Jenkins](https://i.ibb.co/xssW1zZ/Screenshot-2024-04-18-162415.png)
![JenkinsParameter](https://i.ibb.co/Cht3DKj/Screenshot-2024-04-18-162514.png)
![OpenshiftDeployment](https://i.ibb.co/N6RK8Xr/Screenshot-2024-04-18-162623.png)
![OpenshiftService](https://i.ibb.co/xsVvhTT/Screenshot-2024-04-18-162719.png)
![OpenshiftRoute](https://i.ibb.co/s23JM1v/Screenshot-2024-04-18-162817.png)
![CurlTest](https://i.ibb.co/WHjTrHV/Screenshot-2024-04-18-162948.png)
