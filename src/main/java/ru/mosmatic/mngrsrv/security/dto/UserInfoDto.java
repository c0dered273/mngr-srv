package ru.mosmatic.mngrsrv.security.dto;

import java.util.List;

public record UserInfoDto(String name, List<String> authorities) {
}
