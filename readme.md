## Run in Minikube

# Point Docker to Minikube
eval $(minikube docker-env)

# Build image inside Minikube
docker build -t vehicle-insurance-app .

# Deploy to Kubernetes
kubectl apply -f k8s/

## Accessing the App (WSL Users)

If using WSL, localhost may not work.

Run:

kubectl port-forward pod/<pod-name> 8081:8081

Then find WSL IP:

hostname -I

Access app via:

http://<WSL-IP>:8081/api/test