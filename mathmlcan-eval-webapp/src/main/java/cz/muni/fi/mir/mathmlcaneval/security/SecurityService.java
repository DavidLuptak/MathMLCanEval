package cz.muni.fi.mir.mathmlcaneval.security;

public interface SecurityService {

  MathUser getCurrentUser();

  Long getCurrentUserId();
}
