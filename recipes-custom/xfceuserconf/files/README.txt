# Welcome to the new Christ XFCE image.
# Login credentials are root:root123
#
# The network address is set to DHCP by default. The setting can be changed in:
# /etc/systemd/network/wired0.network
#
# Default:
#	[Match]
#	Name=eth0
#	[Network]
#	DHCP=yes
#
# Example:
#	[Match]
#	Name=eth0
#	[Network]
#	Address=192.168.56.1/24
#
# Save your settings and restart systemd-networkd to reload your configuration
#	systemctl restart systemd-networkd
#
# For more information about possible settings refer to:
# https://www.freedesktop.org/software/systemd/man/systemd.network.html
#
