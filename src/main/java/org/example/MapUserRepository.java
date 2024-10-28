package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class MapUserRepository implements UserRepository {
  private final ConcurrentHashMap<String, User> usersByMsisdn = new ConcurrentHashMap<>();

  @Override
  public User findByMsisdn(String msisdn) {
    return usersByMsisdn.get(msisdn);
  }

  @Override
  public void updateUserByMsisdn(String msisdn, User user) {
    usersByMsisdn.put(msisdn, user);
  }
}
