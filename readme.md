## Run in Minikube

# Point Docker to Minikube
eval $(minikube docker-env)

# Build image inside Minikube
docker build -t vehicle-insurance-app .

# Deploy to Kubernetes
kubectl apply -f k8s/
