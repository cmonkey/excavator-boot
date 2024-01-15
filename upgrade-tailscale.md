  - [upgrade tailscale for openwrt](#sec-1)
    - [download](#sec-1-1)
    - [decompression](#sec-1-2)
    - [check pkg](#sec-1-3)
    - [stop old version](#sec-1-4)
    - [install pkg](#sec-1-5)
    - [start version](#sec-1-6)
    - [check new version](#sec-1-7)

# upgrade tailscale for openwrt<a id="sec-1"></a>

## download<a id="sec-1-1"></a>

```shell
wget https://pkgs.tailscale.com/stable/tailscale_1.56.1_amd64.tgz -O tailscale_1.56.1_amd64.tgz
```

## decompression<a id="sec-1-2"></a>

```shell
tar zxvf tailscale_1.56.1_amd64.tgz
```

## check pkg<a id="sec-1-3"></a>

```shell
cd tailscale_1.5.6.1
./tailscale version
```

## stop old version<a id="sec-1-4"></a>

```shell
service tailscale stop
```

## install pkg<a id="sec-1-5"></a>

```shell
cp tailscale* /usr/sbin/
```

## start version<a id="sec-1-6"></a>

```shell
service tailscale start
```

## check new version<a id="sec-1-7"></a>

```shell
tailscale version
```

<span class="timestamp-wrapper"><span class="timestamp">[2024-01-15 一 14:55]</span></span>
