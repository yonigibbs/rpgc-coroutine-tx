## Postgres docker file for testing

Build image

```
> docker build -t test/rpgc-coroutine-tx postgres
```

Create and run a container from the image above

```
> docker run --name test-rpgc-coroutine-tx -p 5432:5432 test/rpgc-coroutine-tx
```